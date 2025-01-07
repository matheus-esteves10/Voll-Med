package med.voll.api.model.consulta.validacoesCancelamento;

import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.model.consulta.CancelamentoConsulta;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class ValidaMotivoCancelamento implements ValidacaoCancelamento {

    @Autowired
    private ConsultaRepository consultaRepository;


    @Override
    public void valida(DadosCancelamentoConsulta dados) {
        boolean motivoValido = EnumSet.allOf(CancelamentoConsulta.class)
                .contains(dados.motivoCancelamento());
        if (!motivoValido) {
            throw new ValidacaoException("Motivo de cancelamento inválido");
        }
    }
}
