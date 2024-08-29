package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;

@Entity
public class Empresa {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idEmpresa;
    private String nomeEmpresa;
    private String email;
    private String cnpj;
    private String telefone;

    @Lob
    private byte[] imagem;

    public Empresa() {
    }

    public Empresa(int idEmpresa, String nomeEmpresa, String email, String cnpj, String telefone, byte[] imagem) {
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.email = email;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.imagem = imagem;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
