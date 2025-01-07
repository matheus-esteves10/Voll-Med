package med.voll.api.model.consulta.validacoesAgendamento;

import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaPacienteAtivo implements ValidacaoAgendamento {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void valida (DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var pacienteIsAtivo = pacienteRepository.findAtivoById(dadosAgendamentoConsulta.idPaciente());

        if(!pacienteIsAtivo) {
            throw new ValidacaoException("Paciente está inativo");
        }
    }
}
