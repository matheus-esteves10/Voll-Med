package med.voll.api.model.consulta.validacoesAgendamento;

import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidaHorarioAntecedencia implements ValidacaoAgendamento {

    public void valida (DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var dataConsulta = dadosAgendamentoConsulta.data();

        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("A consulta deve ser agendada com antecedencia mínima de 30 minutos");
        }
    }
}
