package med.voll.api.medico;

import med.voll.api.endereco.Endereco;

public record DadosDetalhamentoMedicos(Long id, String nome, String crm, String telefone, Especialidade especialidade, Endereco endereco) {
    public DadosDetalhamentoMedicos(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }
}
