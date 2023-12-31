package br.edu.ifpb.pdist.front.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Paciente implements Serializable {

    // Para garantir que a assinatura de um número seja única , para o uso do @Id
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Este campo é obrigatório!")
    private String nome;

    @Size(min = 11, max = 11, message = "Informe um CPF com 11 dígitos!")
    @NotBlank(message = "Este campo é obrigatório!")
    private String cpf;

    @NotBlank(message = "Este campo é obrigatório!")
    private String sexo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    @Past(message = "Data deve ser no passado")
    private Date dataNascimento;

    @Email(message = "Informe um e-mail válido!")
    @NotBlank(message = "Este campo é obrigatório!")
    private String email;

    private String telefone;

    // Relação entre Paciente e Anamnese
    private String anamnese;
    //@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    //@JoinColumn(name = "id_anamnese") // identificar coluna apenas no ManyToOne
    //@ToString.Exclude
    //private Set<Anamnese> anamineses = new HashSet<Anaminese>(); 

    // Relação entre Paciente e Prontuário
    private String prontuario;
    //@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    //@JoinColumn(name = "id_prontuario") // identificar coluna apenas no ManyToOne
    //@ToString.Exclude
    //private Set<Prontuario> prontuarios = new HashSet<Prontuario>(); 
}

