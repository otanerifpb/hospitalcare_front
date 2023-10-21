package br.edu.ifpb.pdist.front.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@EqualsAndHashCode(exclude = {"instituicao", "declaracoes"})
@AllArgsConstructor
@Entity
public class Medico implements Serializable {

    // Para garantir que a assinatura de um número seja única , para o uso do @Id
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nome;

    private String crm;

    private String sexo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Data deve ser no passado")
    private Date dataNascimento;

    private String especialidade;

    private String telefone;
}
