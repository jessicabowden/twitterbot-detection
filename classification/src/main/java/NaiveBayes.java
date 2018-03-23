import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

/**
 * Created by Jessica on 05/05/2015.
 */
public class NaiveBayes {
    public DocumentCategorizerME buildClassifier() {
        Charset charset = Charset.forName("UTF-8");
        ObjectStream<String> lineStream = null;
        DoccatModel model = null;

        try {
            lineStream = new PlainTextByLineStream(
                    new FileInputStream("allTweets.txt"), charset);
        }
        catch (FileNotFoundException e) {
            System.out.println("Tweets not found");
        }

        ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

        try {
            model = DocumentCategorizerME.train("en", sampleStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        DocumentCategorizerME documentCategorizerME = new DocumentCategorizerME(model);

        return documentCategorizerME;
    }

    private double[] getResults(String tweet, DocumentCategorizerME categorizerME) {
        return categorizerME.categorize(tweet);
    }

    public String estimateBestCategory(String tweet, DocumentCategorizerME categorizerME) {
        return categorizerME.getBestCategory(getResults(tweet, categorizerME));
    }

    public String allResults(String tweet, DocumentCategorizerME categorizerME) {
        return buildClassifier().getAllResults(getResults(tweet, categorizerME));
    }
}
