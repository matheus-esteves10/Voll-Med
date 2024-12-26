package med.voll.api.dto.medico;

import med.voll.api.model.endereco.Endereco;
import med.voll.api.model.medico.Especialidade;
import med.voll.api.model.medico.Medico;

public record DadosDetalhamentoMedicos(Long id, String nome, String crm, String telefone, Especialidade especialidade, Endereco endereco) {
    public DadosDetalhamentoMedicos(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }
}
