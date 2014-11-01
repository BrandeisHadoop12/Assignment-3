package code.lemma;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import code.articles.GetArticlesMapred;
import edu.umd.cloud9.collection.wikipedia.WikipediaPage;
import utils.StringIntegerList;
import utils.WikipediaPageInputFormat;

public class StopWordRemoval {
  
  public Tokenizer() {
    
  }
  
  public static void main(String[] args) {
    
  }
}