package br.com.navita.api.web.payload;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class PageableDTO implements Serializable {

	private static final long serialVersionUID = 8478476457681268420L;

	@Min(0)
    private Integer page;

    @Min(0)
    @Max(100)
    private Integer limit;

    public boolean isEmpty() {
         return this.page == null && this.limit == null;
    }
}
