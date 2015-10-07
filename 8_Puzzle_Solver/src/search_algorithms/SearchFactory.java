package search_algorithms;

/***
 * A static Factory class with methods to return new objects of SearchAlgorithm classes
 * 
 * @author Ricardo Arroyo
 *
 */
public class SearchFactory {
	
	private SearchFactory(){}
	
	/***
	 * Create a new AStar search object  
	 * @return A new AStar Object
	 */
	public static ISearchAlgorithm createAStartSearch(){
		return new AStar();
	}

	/***
	 * Create a new BreadthFirst search object  
	 * @return A new BreadthFirst Object
	 */
	public static ISearchAlgorithm createBreadthFirstSearch(){
		return new BreadthFirst();
	}

	/***
	 * Create a new DepthFirst search object  
	 * @return A new DepthFirst Object
	 */	
	public static ISearchAlgorithm createDepthFirstSearch(){
		return new DepthFirst();
	}

	/***
	 * Create a new GreedyBreadthFirst search object  
	 * @return A new GreedyBreadthFirst Object
	 */	
	public static ISearchAlgorithm createGreedyBFS(){
		return new GreedyBFS(); 
	}
	
}
