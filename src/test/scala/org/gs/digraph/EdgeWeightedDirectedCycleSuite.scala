/**
 * ScalaTest, JUnit tests for BellmanFordSP
 */
package org.gs.digraph

import org.gs.digraph.fixtures.DirectedEdgeBuilder
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class EdgeWeightedDirectedCycleSuite extends FlatSpec {
  it should "certify that digraph is either acyclic or has a directed cycle" in new DirectedEdgeBuilder {
    
    val managedResource = readURI("http://algs4.cs.princeton.edu/44sp/tinyEWDn.txt")
    val tuple = managedResource.loan(readFileToTuple)
    val g = new EdgeWeightedDigraph(tuple._1)

    for (ed <- tuple._3) g.addEdge(ed)
    val a = new EdgeWeightedDirectedCycle(g)
    if (a.hasCycle) {
      var first = null.asInstanceOf[DirectedEdge]
      var last = null.asInstanceOf[DirectedEdge]
      for(e <- a.cycle) { 
        if(first == null) first = e
        if(last != null) {
          if(last.to != e.from) fail(s"cycle edges last:$last and e:$e not incident")
        }
        last = e 
      }
      if(last.to != first.from) fail(s"cycle edges last:$last and first:$first not incident")
    }
  }
}