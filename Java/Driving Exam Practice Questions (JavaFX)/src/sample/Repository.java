package sample;

import java.io.*;
import java.util.*;

public class Repository {
    List<Frage> fragebogen = new ArrayList<Frage>();

    public Repository() {
        List<Integer> list = generateRandomId();
        File file = new File("C:\\Lab5\\src\\sample\\Fragen");
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null) {
                Frage frage = new Frage(line);
                if (list.contains(frage.getId()))
                    fragebogen.add(frage);
            }
            Collections.shuffle(fragebogen);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Frage> getFragebogen() {
        return fragebogen;
    }

    public void setFragebogen(List<Frage> fragebogen) {
        this.fragebogen = fragebogen;
    }

    public List<Integer> generateRandomId() {
        List<Integer> list = new ArrayList<Integer>();
        Random rn = new Random();
        for (int i = 0; i < 26; i++) {
            int randomInt;
            do {
                randomInt = rn.nextInt(41);
            } while (list.contains(randomInt));
            list.add(randomInt);
        }
        return list;
    }

    public Iterator<Frage> findAll() {
        return getFragebogen().iterator();
    }
}
