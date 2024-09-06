// Generated methods can be refined, such as copy().
// .options: flush
// .grammar: { Node; }
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();
		
		try {
			testNull(node.clone());
			testNull(node.copy());
			testNull(node.fullCopy());
			
			node.flushAttrCache();
			node.flushCollectionCache();
			test(node.cacheFlushed);
			test(node.collectionCacheFlushed);
		} catch (CloneNotSupportedException e) {
			test(false);
		}
	}
}
