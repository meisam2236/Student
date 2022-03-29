package com.student.app.helper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Geometry {
    private static final Logger log = LoggerFactory.getLogger(GradeLogger.class);
    public static String intersectMessage(LineString line, Polygon polygon) {
        return line + " did " + (!line.crosses(polygon) ? "not " : "") + "crossed " + polygon;
    }
    @PostConstruct
    public static void createShapes(){
        GeometryFactory geometryFactory = new GeometryFactory();
        Polygon triangle = geometryFactory.createPolygon(new Coordinate[]{
                new Coordinate(1,1), // starting point
                new Coordinate(3,5),
                new Coordinate(5,1),
                new Coordinate(1,1), // ending point
        });
        LineString crossedLine = geometryFactory.createLineString(new Coordinate[]{
                new Coordinate(3,3),
                new Coordinate(5,5)});
        LineString uncrossedLine = geometryFactory.createLineString(new Coordinate[]{
                new Coordinate(1,9),
                new Coordinate(9,1)});
        log.info(intersectMessage(crossedLine, triangle));
        log.info(intersectMessage(uncrossedLine, triangle));
    }
}
