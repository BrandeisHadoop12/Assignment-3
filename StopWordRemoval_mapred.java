package StopWords;

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

public class StopWordRemoval_mapred {
	public static class StopWordRemovalMapper extends Mapper<LongWritable, WikipediaPage, Text, StringIntegerList> {

		@Override public void map(LongWritable offset, WikipediaPage page, Context context) throws IOException,
				InterruptedException {
			// Parses input into tokens, then uses hashmap to compute word frequencies across input.
			Tokenizer tokenizer = new Tokenizer();
			Map<String, Integer> hashMap = new HashMap<String, Integer>();
			for (String token : tokenizer.tokenize(page.getContent())) {
				if (hashMap.containsKey(token)) {
					hashMap.put(token, hashMap.get(token) + 1);
				} else {
					hashMap.put(token, 1);
				}
			}
			StringIntegerList list = new StringIntegerList(hashMap);
			context.write(new Text(page.getTitle()), list);
		}
	}
	
	public static void main (String[] args) throws IOException {
		Job conf = Job.getInstance(new Configuration(), "LemmaIndexMapred");
		conf.setJarByClass(LemmaIndexMapred.class);
		conf.setMapperClass(LemmaIndexMapper.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(StringIntegerList.class);
		conf.setInputFormatClass(WikipediaPageInputFormat.class);
		conf.setOutputFormatClass(TextOutputFormat.class);
		URI ur = null;
		try {
			ur = new URI("/user/hadoop12/stop_words.txt");
		} catch (URISyntaxException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		conf.addCacheFile(ur);
		conf.getConfiguration().set("mapreduce.job.queuename","hadoop12");
		try {
			FileInputFormat.addInputPath(conf, new Path(args[0]));
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		try {
			conf.waitForCompletion(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
