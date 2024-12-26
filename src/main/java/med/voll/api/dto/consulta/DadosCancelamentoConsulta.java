package med.voll.api.dto.consulta;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.consulta.CancelamentoConsulta;

public record DadosCancelamentoConsulta(@NotNull
                                        DadosDetalhamentoConsulta dadosDetalhamentoConsulta,
                                        @NotNull
                                        CancelamentoConsulta motivoCancelamento) {

}
