package scala.collection
package mutable

import scala.annotation.{nowarn, tailrec}
import scala.collection.Stepper.EfficientSplit
import scala.collection.generic.DefaultSerializationProxy
import scala.util.hashing.MurmurHash3

class HashMap[K, V](initialCapacity: Int, loadFactor: Double)
		extends AbstractMap[K, V]
				with MapOps[K, V, HashMap, HashMap[K, V]]
				with StrictOptimizedIterableOps[(K, V), Iterable, HashMap[K, V]]
				with StrictOptimizedMapOps[K, V, HashMap, HashMap[K, V]]
				with MapFactoryDefaults[K, V, HashMap, Iterable]
				with Serializable {


	def this() = this(HashMap.defaultInitialCapacity, HashMap.defaultLoadFactor)

	import HashMap.Node

	/** The actual hash table. */
	private[this] var table = new Array[Node[K, V]](tableSizeFor(initialCapacity))

	/** The next size value at which to resize (capacity * load factor). */
	private[this] var threshold: Int = newThreshold(table.length)

	private[this] var contentSize = 0

	override def size: Int = contentSize
}