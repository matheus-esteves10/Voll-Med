package med.voll.api.model.consulta.validacoesAgendamento;

import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaMedicoAtivo implements ValidacaoAgendamento {

    @Autowired
    private MedicoRepository medicoRepository;

    public void valida(DadosAgendamentoConsulta dadosAgendamentoConsulta) {

        if(dadosAgendamentoConsulta.idMedico() == null) { //validação pelo id do médico ser um optional
            return;
        }

        var medicoIsAtivo = medicoRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());

        if (!medicoIsAtivo) {
            throw new ValidacaoException("Médico está inativo");
        }
    }
}
