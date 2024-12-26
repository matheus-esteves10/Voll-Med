package med.voll.api.service;

import med.voll.api.dto.consulta.DadosDetalhamentoConsulta;
import med.voll.api.model.consulta.CancelamentoConsulta;
import med.voll.api.model.consulta.Consulta;
import med.voll.api.model.consulta.validacoesConsulta.ValidacaoConsulta;
import med.voll.api.model.medico.Medico;
import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidacaoConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe");
        }

        validadores.forEach(v -> v.valida(dados));


        var medico = escolherMedico(dados);
        if (medico == null) {
            throw new ValidacaoException("Nenhum médico disponível na especialidade");
        }
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade obrigatória quando médico escolhido for aleatório");
        }

        return medicoRepository.escolherMedicoAleatorio(dados.especialidade(), dados.data());

    }

    public void cancelar (DadosCancelamentoConsulta dados){
        if (dados.motivoCancelamento() == null) {
            throw new ValidacaoException("É necessário a justificativa do motivo para cancelar a consulta");
        }


        boolean motivoValido = EnumSet.allOf(CancelamentoConsulta.class)
                .contains(dados.motivoCancelamento());
        if (!motivoValido) {
            throw new ValidacaoException("Motivo de cancelamento inválido");
        }

        if (!consultaRepository.existsById(dados.dadosDetalhamentoConsulta().id())) {
            throw new ValidacaoException("A consulta não existe no banco de dados");
        }

        var dataAtual = LocalDateTime.now();
        var diferencaMinima = Duration.between(dataAtual, dados.dadosDetalhamentoConsulta().data()).toHours();

        if (diferencaMinima < 24) {
            throw new ValidacaoException("Para cancelar a consulta é necessário pelo menos 24h de antecedencia");
        }

        consultaRepository.deleteById(dados.dadosDetalhamentoConsulta().id());
    }
}
