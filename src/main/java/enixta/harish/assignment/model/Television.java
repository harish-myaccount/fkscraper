package enixta.harish.assignment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Television {

	@Id
	private String productId;
	@NotNull
	private String modelName;
	private TVType type;
	private Integer screenSize;
	private Integer price;
	private String productUrl;

}
