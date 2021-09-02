package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws BlueprintNotFoundException, BlueprintPersistenceException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        String author = "Mina";
        BlueprintsServices blueprintsServices = applicationContext.getBean(BlueprintsServices.class);
        Set<Blueprint> allBlueprints = blueprintsServices.getAllBlueprints();
        blueprintsServices.addNewBlueprint(new Blueprint("Mina", "Jayco"));
        blueprintsServices.getBlueprint("Mina", "Jayco");
        Point[] pts = new Point[] { new Point(1, 1), new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(3, 3),
                new Point(4, 4), new Point(5, 5), new Point(6, 6), new Point(7, 7) };
        blueprintsServices.addNewBlueprint(new Blueprint("Mina", "Jayco2", pts));
        blueprintsServices.getBlueprint("Mina", "Jayco2");
        System.out.println("Ejecutado sin Fallos");

    }

}
