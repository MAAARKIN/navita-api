package br.com.navita.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.navita.api.domain.model.Brand;
import br.com.navita.api.domain.repository.BrandRepository;
import br.com.navita.api.exception.AlreadyExistException;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

	@Mock
	private BrandRepository repository;
	@InjectMocks
	private BrandService service;
	
	@Test
	public void shouldSaveNewBrand() {
		
		Brand brandMocked = new Brand();
		brandMocked.setName("Navita");
		brandMocked.setId(10L);
		
		Brand brand = new Brand();
		brand.setName("Navita");
		
		
		when(repository.save(Mockito.any(Brand.class))).thenReturn(brandMocked);
		
		Brand save = service.save(brand);
		
		assertEquals(brandMocked.getId(), save.getId());
		assertEquals(brandMocked.getName(), save.getName());
	}
	
	@Test
	public void catchAlreadyExistRecord() {
		Brand brand = new Brand();
		brand.setName("Navita");
		
		when(repository.exists(Mockito.any())).thenReturn(true);
		
		Exception exception = assertThrows(AlreadyExistException.class, () -> {
			service.save(brand);
		});

		String expectedMessage = "already exist a brand, consider change the name";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void shouldFindABrand() {
		
		Brand brand = new Brand();
		brand .setName("Navita");
		brand.setId(10L);
		
		Optional<Brand> opBrand = Optional.of(brand);
		
		when(repository.findById(10L)).thenReturn(opBrand);
		
		Brand find = service.find(10L);
		
		assertEquals(opBrand.get().getName(), find.getName());
	}
}
