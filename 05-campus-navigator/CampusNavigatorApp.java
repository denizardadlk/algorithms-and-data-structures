import java.io.Serializable;
import java.util.*;
import java.util.spi.CurrencyNameProvider;

public class CampusNavigatorApp implements Serializable {
    static final long serialVersionUID = 99L;

    public HashMap<Station, Station> predecessors = new HashMap<>();
    public HashMap<Set<Station>, Double> times = new HashMap<>();

    public CampusNavigatorNetwork readCampusNavigatorNetwork(String filename) {
        CampusNavigatorNetwork network = new CampusNavigatorNetwork();
        network.readInput(filename);
        return network;
    }

    public class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    /**
     * Calculates the fastest route from the user's selected starting point to
     * the desired destination, using the campus golf cart network and walking
     * paths.
     * 
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(CampusNavigatorNetwork network) {
        List<RouteDirection> routeDirections = new ArrayList<>();
        // TODO: Your code goes here
        List<Station> nodes = new ArrayList<Station>();
        nodes.add(network.startPoint);
        for (CartLine line : network.lines) {
            List<Station> stations = new ArrayList<>();
            for (Station station : line.cartLineStations) {
                if (!stations.contains(station)) {
                    nodes.add(station);
                }
            }
        }
        nodes.add(network.destinationPoint);
        
        HashMap<Station, List<Pair<Station, Double>>> twoDList = new HashMap<Station, List<Pair<Station, Double>>>();
        String station1_name;
        String station2_name;
        String station1_name_check;
        String station2_name_check;
        Double distance = 0.00;
        for (Station station1 : nodes) {
            List<Pair<Station, Double>> temp = new ArrayList<>(); 
            for (Station station2 : nodes) {
                station1_name = station1.description;
                station2_name = station2.description;
                station1_name_check = station1_name.substring(0, station1_name.length() - 1);
                station2_name_check = station2_name.substring(0, station2_name.length() - 1);
                int station1_int=0;
                int station2_int=0;
                try {
                    station1_int = Integer.parseInt(station1_name.substring(station1_name.length() - 1, station1_name.length()));
                    station2_int = Integer.parseInt(station2_name.substring(station2_name.length() - 1, station2_name.length()));
                } catch (NumberFormatException e) {
                    station1_int=0;
                    station2_int=0;
                }
                
                if (station1_name_check.equals(station2_name_check) && Math.abs(station2_int-station1_int)==1) {
                    distance = Math.sqrt(Math.pow(Math.abs(station1.coordinates.x - station2.coordinates.x), 2) +
                            Math.pow(Math.abs(station1.coordinates.y - station2.coordinates.y), 2)) /
                            (network.averageCartSpeed);
                } else {
                    distance = Math.sqrt((Math.pow(Math.abs(station1.coordinates.x - station2.coordinates.x), 2) +
                            Math.pow(Math.abs(station1.coordinates.y - station2.coordinates.y), 2)) )/ network.averageWalkingSpeed;
                }
                distance = Math.round(distance * 100.0) / 100.0;
                temp.add(new Pair<>(station2, distance));
            }
            twoDList.put(station1, temp);
        }
        

        HashMap<Station, Double> distances = new HashMap<>();
        HashMap<Station, Station> predecessors = new HashMap<>();
        PriorityQueue<Pair<Station, Double>> priorityQueue = new PriorityQueue<>(
                Comparator.comparingDouble(Pair::getValue));

        for (Station station : nodes) {
            distances.put(station, 10000000000.00);
        }
        distances.replace(network.startPoint, 0.0);
        priorityQueue.add(new Pair<>(network.startPoint, 0.0));

        while (!priorityQueue.isEmpty()) {
            Pair<Station, Double> cur = priorityQueue.poll();
            Station currentStation = cur.getKey();
            double currentDistance = cur.getValue();

            if (currentDistance > distances.get(currentStation)) {
                continue;
            }

            List<Pair<Station, Double>> neighbors = twoDList.get(currentStation);
            if (neighbors != null) {
                for (Pair<Station, Double> neighbor : neighbors) {
                    Station neighborStation = neighbor.getKey();
                    double dist = neighbor.getValue();
                    double newDistance = currentDistance + dist;

                    if (newDistance < distances.get(neighborStation)) {
                        distances.replace(neighborStation, newDistance); 
                        predecessors.put(neighborStation, currentStation); 
                        priorityQueue.add(new Pair<>(neighborStation, newDistance));
                    }
                }
            }
        }

        Station current = network.destinationPoint;
        while (current != null && predecessors.containsKey(current)) {
            Station previous = predecessors.get(current);
            station1_name_check = previous.description.substring(0, previous.description.length() - 1);
            station2_name_check = current.description.substring(0, current.description.length() - 1);
            if (previous != null) {
                routeDirections.add(0,
                        new RouteDirection(previous.description, current.description,
                                distances.get(current) - distances.get(previous),
                                station1_name_check.equals(station2_name_check)));
            }
            current = previous;
        }
        return routeDirections;
    }

    /**
     * Function to print the route directions to STDOUT
     * Directions
        ----------
        1. Walk from "Starting Point" to "Main Avenue Path Station 1" for 1.20 minutes.
        2. Ride the cart from "Main Avenue Path Station 1" to "Main Avenue Path Station 2" for 0.75 minutes.
        3. Walk from "Main Avenue Path Station 2" to "Library Loop Station 2" for 2.40 minutes.
        4. Ride the cart from "Library Loop Station 2" to "Library Loop Station 3" for 0.75 minutes.
        5. Walk from "Library Loop Station 3" to "Final Destination" for 2.40 minutes.
        ### CAMPUS NAVIGATOR END ###
     */
    public void printRouteDirections(List<RouteDirection> directions) {
        double sum = 0;
        for (RouteDirection iterable_element : directions) {
            sum+=iterable_element.duration;
        }
        System.out.printf("The fastest route takes %.0f minute(s).\n", sum);
        System.out.println("Directions");
        System.out.println("----------");
        for (int i = 1; i < directions.size()+1; i++) {
            RouteDirection cur = directions.get(i-1);
            System.out.print(String.valueOf(i)+". ");
            if(cur.cartRide){
                System.out.print("Ride the cart from ");
            }
            else{
                System.out.print("Walk from ");
            }
            System.out.print("\"" + cur.startStationName + "\" to \"" + cur.endStationName + "\" for ");
            System.out.printf("%.2f minutes.\n", cur.duration);
    }
        }

    }

