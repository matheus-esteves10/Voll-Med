package med.voll.api.model.consulta.validacoesCancelamento;

import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidaTempoCancelamento implements ValidacaoCancelamento{
    @Override
    public void valida(DadosCancelamentoConsulta dados) {
        var dataAtual = LocalDateTime.now();
        var diferencaMinima = Duration.between(dataAtual, dados.dadosDetalhamentoConsulta().data()).toHours();
        System.out.println("Diferença mínima:" + diferencaMinima);

        if (diferencaMinima < 24) {
            throw new ValidacaoException("Para cancelar a consulta é necessário pelo menos 24h de antecedencia");
        }
    }
}
