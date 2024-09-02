package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;
    private String nomeEmpresa;
    private String emailEmpresa;
    private String cnpj;
    private String telefone;

    private Integer fkAssinante;

    @Lob
    private byte[] imagem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return idEmpresa == empresa.idEmpresa && Objects.equals(nomeEmpresa, empresa.nomeEmpresa) && Objects.equals(emailEmpresa, empresa.emailEmpresa) && Objects.equals(cnpj, empresa.cnpj) && Objects.equals(telefone, empresa.telefone) && Objects.equals(fkAssinante, empresa.fkAssinante) && Objects.deepEquals(imagem, empresa.imagem);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idEmpresa);
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getFkAssinante() {
        return fkAssinante;
    }

    public void setFkAssinante(Integer fkAssinante) {
        this.fkAssinante = fkAssinante;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
