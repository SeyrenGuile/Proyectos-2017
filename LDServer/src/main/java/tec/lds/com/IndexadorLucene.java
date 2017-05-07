package tec.lds.com;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;


public class IndexadorLucene {
    
    private String origen;
    private String[] nombreColecciones;
    
    // Este constructor recibe la ruta origen donde se encuentran las colecciones
    public IndexadorLucene(String ruta){
        origen = ruta;
        inicializarColecciones();
    }
    
    public String[] getNombreColecciones(){
        return nombreColecciones;
    }
    
    /*
    Este metodo permite conocer el numero de colecciones que se encuentran en la
    carpeta origen
    */
    private void inicializarColecciones(){
         File file = new File(origen);
         String[] coleccionesGeneral = file.list(new FilenameFilter() {
             public boolean accept(File f, String name) {
                 return f.isDirectory();
             }
         });
         nombreColecciones = coleccionesGeneral;
    }
    
    /*
    Este metodo se encarga de realizar una consulta en el indice invertido y retorna
    una lista de urls con los documentos que coincidan con la consulta
    */
    public List<String> encontrar(String palabra) throws IOException, ParseException{
        
        //Se especifica donde se lee el indice invertido
        Path path  = Paths.get(origen);
        IndexReader ireader = DirectoryReader.open(FSDirectory.open(path));
        IndexSearcher isearch = new IndexSearcher(ireader);
        
        //Se especifica en que seccion de los documentos se buscara y la palabra a buscar
        QueryParser parser = new QueryParser("contenido", new StandardAnalyzer());
        Query query = parser.parse(palabra);
        
        //Se obtienen los primeros 40 documentos del ranking y sus urls
        ScoreDoc[] hits = isearch.search(query, 40).scoreDocs;
        List<String> urls = new ArrayList<String>();
        for(ScoreDoc hit : hits){
            urls.add(isearch.doc(hit.doc).get("nombre"));
        }
        return urls;
    }
    public List<String> encontrarCategoria(String palabra) throws IOException, ParseException{
        
        //Se especifica donde se lee el indice invertido
        Path path  = Paths.get(origen);
        IndexReader ireader = DirectoryReader.open(FSDirectory.open(path));
        IndexSearcher isearch = new IndexSearcher(ireader);
        
        //Se especifica en que seccion de los documentos se buscara y la palabra a buscar
        QueryParser parser = new QueryParser("categoria", new StandardAnalyzer());
        Query query = parser.parse(palabra);
        
        //Se obtienen los primeros 40 documentos del ranking y sus urls
        ScoreDoc[] hits = isearch.search(query, 40).scoreDocs;
        List<String> urls = new ArrayList<String>();
        for(ScoreDoc hit : hits){
            urls.add(isearch.doc(hit.doc).get("nombre"));
        }
        return urls;
    }
    /*
    Este metodo se encarga de realizar una consulta en el indice invertido con la posicion del documento
    que se requiere y retorna un String con el contenido del Documento
    */
    public String getContenido(String palabra, String pos) throws IOException, ParseException{
        
        //Se especifica donde se lee el indice invertido
        Path path  = Paths.get(origen);
        IndexReader ireader = DirectoryReader.open(FSDirectory.open(path));
        IndexSearcher isearch = new IndexSearcher(ireader);
        
        //Se especifica en que seccion de los documentos se buscara y la palabra a buscar
        QueryParser parser = new QueryParser("contenido", new StandardAnalyzer());
        Query query = parser.parse(palabra);
        
        //Se obtiene el contenido del documento solicitado
        ScoreDoc[] hits = isearch.search(query, 40).scoreDocs;
        List<String> contenidos = new ArrayList<String>();
        for(ScoreDoc hit : hits){
            contenidos.add(isearch.doc(hit.doc).get("contenido"));
        }
        // Se obtiene el de la posicion especifica
        String resultado = contenidos.get(Integer.parseInt(pos));
        return resultado;
    }
        public String getContenidoCategoria(String categoria, String pos) throws IOException, ParseException{
        
        //Se especifica donde se lee el indice invertido
        Path path  = Paths.get(origen);
        IndexReader ireader = DirectoryReader.open(FSDirectory.open(path));
        IndexSearcher isearch = new IndexSearcher(ireader);
        
        //Se especifica en que seccion de los documentos se buscara y la palabra a buscar
        QueryParser parser = new QueryParser("categoria", new StandardAnalyzer());
        Query query = parser.parse(categoria);
        
        //Se obtiene el contenido del documento solicitado
        ScoreDoc[] hits = isearch.search(query, 40).scoreDocs;
        List<String> contenidos = new ArrayList<String>();
        for(ScoreDoc hit : hits){
            contenidos.add(isearch.doc(hit.doc).get("contenido"));
        }
        // Se obtiene el de la posicion especifica
        String resultado = contenidos.get(Integer.parseInt(pos));
        return resultado;
    }
}
