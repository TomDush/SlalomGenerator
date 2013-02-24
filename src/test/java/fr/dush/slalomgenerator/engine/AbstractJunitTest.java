package fr.dush.slalomgenerator.engine;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/slalomgenerator-context.xml")
public abstract class AbstractJunitTest {

	public AbstractJunitTest() {
		MockitoAnnotations.initMocks(this);
	}
	
}
