package med.voll.api.model.consulta.validacoesAgendamento;

import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaOutraAgendamentoNoDia implements ValidacaoAgendamento {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void valida (DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var primeiroHorario = dadosAgendamentoConsulta.data().withHour(7);
        var ultimoHorario = dadosAgendamentoConsulta.data().withHour(18);
        var pacienteTemOutraConsulta = consultaRepository.existsByPacienteIdAndDataBetween(dadosAgendamentoConsulta.idPaciente(), primeiroHorario, ultimoHorario);

        if(pacienteTemOutraConsulta) {
            throw new ValidacaoException("O paciente não pode ter outra consulta marcada no dia na clínica");
        }
    }
}
