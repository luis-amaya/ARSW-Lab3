package edu.eci.arsw.blueprints.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.Filters.Filter;
import edu.eci.arsw.blueprints.Filters.impl.SubsamplingFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

@Component
public class FiltersTest {
    public BlueprintsServices services;
    public InMemoryBlueprintPersistence ibpp;
    public Filter filter;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        services = ac.getBean(BlueprintsServices.class);
        ibpp = new InMemoryBlueprintPersistence();
        filter = new SubsamplingFilter();
    }

    @Test
    public void deberiaFiltrarUnPlanoPorSubMuestreo() {
        try {
            Point[] pts = new Point[] { new Point(0, 0), new Point(0, 0), new Point(10, 10), new Point(10, 10) };
            List<Point> pts2 = Arrays
                    .asList(new Point[] { new Point(0, 0), new Point(0, 0), new Point(10, 10), new Point(10, 10) });

            Blueprint bp = new Blueprint("john", "thepaint", pts);

            ibpp.saveBlueprint(bp);
            Blueprint blueprint = ibpp.getBlueprint("john", "thepaint");
            blueprint = filter.blueprintFilter(blueprint);

            for (int i = 0; i < blueprint.getPoints().size(); i++) {
                int x1, y1, x2, y2;
                x1 = blueprint.getPoints().get(i).getX();
                y1 = blueprint.getPoints().get(i).getY();
                x2 = pts2.get(i).getX();
                y2 = pts2.get(i).getX();
                assertEquals(x1, x2);
                assertEquals(y1, y2);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Fallo que no deberia de activarse");
        }
    }

    @Test
    public void deberiaFiltrarUnPlanoPorRedundancia() {

        try {
            Point[] pts = new Point[] { new Point(0, 0), new Point(0, 0), new Point(10, 10), new Point(10, 10) };
            Blueprint bp = new Blueprint("john", "thepaint", pts);

            services.addNewBlueprint(bp);
            Blueprint blueprint = services.getBlueprint("john", "thepaint");

            assertEquals(blueprint.getPoints().size(), 4);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Fallo que no deberia de activarse");
        }
    }
}
