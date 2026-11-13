import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class AlienFlora {
    private File xmlFile;
    ArrayList<GenomeCluster> clusters = new ArrayList<>();
    Map<String, String> evolutionPairs = new HashMap<>();
    List<String[]> Adaptations = new ArrayList<>();

    public AlienFlora(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void readGenomes() {
        ArrayList<Genome> all_genomes = new ArrayList<Genome>();
        ArrayList<String> ids = new ArrayList<>();
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            String id = "";
            int evolutionFactor = 0;

            NodeList genomeList = document.getElementsByTagName("genome");
            for (int i = 0; i < genomeList.getLength(); i++) {
                List<Map.Entry<String, Integer>> links = new ArrayList<>();
                Node genomeNode = genomeList.item(i);

                if (genomeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element genomeElement = (Element) genomeNode;

                    id = genomeElement.getElementsByTagName("id").item(0).getTextContent();

                    evolutionFactor = Integer
                            .parseInt(genomeElement.getElementsByTagName("evolutionFactor").item(0).getTextContent());

                    NodeList linkList = genomeElement.getElementsByTagName("link");
                    for (int j = 0; j < linkList.getLength(); j++) {
                        Node linkNode = linkList.item(j);

                        if (linkNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element linkElement = (Element) linkNode;

                            String target = linkElement.getElementsByTagName("target").item(0).getTextContent();

                            // Read the <adaptationFactor> element
                            Integer adaptationFactor = Integer.parseInt(
                                    linkElement.getElementsByTagName("adaptationFactor").item(0).getTextContent());
                            links.add(new AbstractMap.SimpleEntry<>(target, adaptationFactor));
                        }
                    }
                }
                Genome temp = new Genome(id, evolutionFactor);
                for (Map.Entry<String, Integer> j : links) {
                    temp.addLink(j.getKey(), j.getValue());
                }
                all_genomes.add(temp);
                ids.add(id);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int index = 0; index < ids.size(); index++) {
            boolean needed = true;
            for (GenomeCluster cluster : clusters) {
                if (cluster.contains(ids.get(index))) {
                    needed = false;
                }
            }
            if (needed) {
                Queue<Integer> myQueue = new LinkedList<>();
                myQueue.add(index);
                GenomeCluster new_cluster = new GenomeCluster();
                while (!myQueue.isEmpty()) {
                    int i = myQueue.remove();
                    Genome gen = all_genomes.get(i);
                    if (new_cluster.contains(gen.id)) {
                        continue;
                    }
                    new_cluster.addGenome(gen);
                    for (Genome.Link iterable_element : gen.links) {
                        myQueue.add(ids.indexOf(iterable_element.target));
                    }
                }
                clusters.add(new_cluster);
            }
        }
        System.out.println("##Start Reading Flora Genomes##");
        System.out.println("Number of Genome Clusters: " + clusters.size());
        List<List<String>> clusterGenomes = new ArrayList<>();
        for (GenomeCluster cluster : clusters) {
            clusterGenomes.add(cluster.nodes()); // Assuming `nodes()` returns a list of genome IDs
        }
        System.out.print("For the Genomes: ");
        System.out.println(clusterGenomes);
        System.out.println("##Reading Flora Genomes Completed##");
    }

    // - Create clusters

    // - Print number of clusters and their genome IDs

    public void evaluateEvolutions() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList evolutionPairsList = document.getElementsByTagName("possibleEvolutionPairs");

            if (evolutionPairsList.getLength() > 0) {
                Node evolutionPairsNode = evolutionPairsList.item(0);

                if (evolutionPairsNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element evolutionPairsElement = (Element) evolutionPairsNode;

                    NodeList pairs = evolutionPairsElement.getElementsByTagName("pair");
                    for (int i = 0; i < pairs.getLength(); i++) {
                        Node pair = pairs.item(i);

                        if (pair.getNodeType() == Node.ELEMENT_NODE) {
                            Element pairElement = (Element) pair;

                            String id1 = pairElement.getElementsByTagName("firstId").item(0).getTextContent();
                            String id2 = pairElement.getElementsByTagName("secondId").item(0).getTextContent();

                            evolutionPairs.put(id1, id2);
                        }
                    }
                }
            }
            
            int valids=0;
            ArrayList<Float> ret= new ArrayList<>();
            for (Map.Entry<String, String> entry : evolutionPairs.entrySet()) {
                GenomeCluster cluster1=null;
                GenomeCluster cluster2=null;

                for (GenomeCluster i : clusters) {
                    if(i.contains(entry.getKey())){
                        cluster1=i;
                    }
                    if(i.contains(entry.getValue())){
                        cluster2=i;
                    }
                }
                if(!cluster1.equals(cluster2)){
                    valids+=1;
                    Float min1 = (float) cluster1.getMinEvolutionGenome().evolutionFactor;
                    int min2=cluster2.getMinEvolutionGenome().evolutionFactor;
                    ret.add((min1+min2)/2);
                }
                else{
                    ret.add(-1.0f);
                }
                
            }
            System.out.println("##Start Evaluating Possible Evolutions##");
            System.out.println("Number of Possible Evolutions: " + evolutionPairs.size());
            System.out.println("Number of Certified Evolutions: " + valids);
            System.out.println("Evolution Factor for Each Evolution Pair: " + ret);
            System.out.println("##Evaluated Possible Evolutions##");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void evaluateAdaptations() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList adaptationPairsList = document.getElementsByTagName("possibleAdaptationPairs");

            if (adaptationPairsList.getLength() > 0) {
                Node adaptationPairsNode = adaptationPairsList.item(0);

                if (adaptationPairsNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element adaptationPairsElement = (Element) adaptationPairsNode;

                    NodeList pairs = adaptationPairsElement.getElementsByTagName("pair");
                    for (int i = 0; i < pairs.getLength(); i++) {
                        Node pair = pairs.item(i);

                        if (pair.getNodeType() == Node.ELEMENT_NODE) {
                            Element pairElement = (Element) pair;


                            Node firstIdNode = pairElement.getElementsByTagName("firstId").item(0);
                            Node secondIdNode = pairElement.getElementsByTagName("secondId").item(0);



                            String id1 = firstIdNode.getTextContent();
                            String id2 = secondIdNode.getTextContent();

                            Adaptations.add(new String[]{id1, id2});

                        }
                    }
                }
            }
            
            int valids=0;
            ArrayList<Integer> ret= new ArrayList<>();
            for ( String[] entry : Adaptations) {
                GenomeCluster cluster1=null;
                GenomeCluster cluster2=null;

                for (GenomeCluster i : clusters) {
                    if(i.contains(entry[0])){
                        cluster1=i;
                    }
                    if(i.contains(entry[1])){
                        cluster2=i;
                    }
                }
                if(cluster1.equals(cluster2)){
                    valids+=1;
                    int temp =  cluster1.dijkstra(entry[0], entry[1]);
                    ret.add(temp);
                }
                else{
                    ret.add(-1);
                }
                
            }
        System.out.println("##Start Evaluating Possible Adaptations##");
        System.out.println("Number of Possible Adaptations: "+ Adaptations.size());
        System.out.println("Number of Certified Adaptations: " + valids);
        System.out.println("Adaptation Factor for Each Adaptation Pair: " + ret);
        System.out.println("##Evaluated Possible Evolutions##");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
