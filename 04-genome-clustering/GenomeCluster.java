import java.util.*;
import java.util.stream.Stream;

public class GenomeCluster {
    public Map<String, Genome> genomeMap = new HashMap<>();
    public ArrayList<String> links;
    ArrayList<String> nodes = new ArrayList<>();

    public void addGenome(Genome genome) {
        // TODO: Add genome to the cluster
        genomeMap.put(genome.id, genome);
    }

    public boolean contains_link(String genomeId) {
        // TODO: Return true if the genome is in the cluster
        for (String iterable_element : links) {
            if (iterable_element.equals(genomeId)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> nodes() {
        ArrayList<String> nodes2 = new ArrayList<>();
        for (Map.Entry<String, Genome> iterable_element : genomeMap.entrySet()) {
            nodes2.add(iterable_element.getKey());
            nodes.add(iterable_element.getKey());

        }
        return nodes2;
    }

    public boolean contains(String genomeId) {
        // TODO: Return true if the genome is in the cluster
        for (String iterable_element : genomeMap.keySet()) {
            if (iterable_element.equals(genomeId)) {
                return true;
            }
        }
        return false;
    }

    public Genome getMinEvolutionGenome() {
        // TODO: Return the genome with minimum evolutionFactor
        int min = 10000000;
        Genome ret = null;
        for (Genome iterable_element : genomeMap.values()) {
            if (iterable_element.evolutionFactor < min) {
                min = iterable_element.evolutionFactor;
                ret = iterable_element;
            }
        }
        return ret;
    }

    public int dijkstra(String startId, String endId) {

        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        queue.add(new AbstractMap.SimpleEntry<>(startId, 0));
        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Map.Entry<String, Integer> cur = queue.poll();
            String id = cur.getKey();
            int sum = cur.getValue();

            if (visited.contains(id)) {
                continue;
            }

            visited.add(id);

            if (id.equals(endId)) {
                return sum;
            }

            Genome node = genomeMap.get(id);

            for (Genome.Link link : node.links) {
                if (!visited.contains(link.target)) {
                    queue.add(new AbstractMap.SimpleEntry<>(link.target, sum + link.adaptationFactor));
                }
            }
        }

        return -1;
    }
}
