package med.voll.api.model.consulta.validacoesConsulta;

import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaExistenciaConsultaMesmoHorario implements ValidacaoConsulta{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void valida (DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var medicoPossuiConsultaNoHorario = consultaRepository.existsByMedicoIdAndData(dadosAgendamentoConsulta.idMedico(), dadosAgendamentoConsulta.data());
        if (medicoPossuiConsultaNoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta nesse horário");
        }
    }
}
