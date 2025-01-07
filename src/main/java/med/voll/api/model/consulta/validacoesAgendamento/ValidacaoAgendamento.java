package med.voll.api.model.consulta.validacoesAgendamento;

import med.voll.api.dto.consulta.DadosAgendamentoConsulta;

public interface ValidacaoAgendamento {

    void valida(DadosAgendamentoConsulta dados);
}
