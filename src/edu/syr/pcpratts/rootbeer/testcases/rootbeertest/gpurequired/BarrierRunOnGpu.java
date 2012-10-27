/* 
 * Copyright 2012 Phil Pratt-Szeliga and other contributors
 * http://chirrup.org/
 * 
 * See the file LICENSE for copying permission.
 */

package edu.syr.pcpratts.rootbeer.testcases.rootbeertest.gpurequired;

import edu.syr.pcpratts.rootbeer.runtime.Kernel;
import edu.syr.pcpratts.rootbeer.runtime.RootbeerGpu;
import java.awt.Robot;

public class BarrierRunOnGpu implements Kernel {

  private int[] m_array;
  private int m_threadId;
  
  public BarrierRunOnGpu(int[] array, int thread_id){
    m_array = array;
    m_threadId = thread_id;
  }
  
  public void gpuMethod() {
    if(RootbeerGpu.isOnGpu()){
      int value = m_array[m_threadId];
      RootbeerGpu.synchthreads();
      int len = m_array.length;
      m_array[len - m_threadId - 1] = value;
    }
  }

  public boolean compare(BarrierRunOnGpu rhs) {
    int[] array = rhs.m_array;
    
    for(int i = 0; i < array.length; ++i ){
      if(array[i] != i){
        System.out.println("failure at: "+i+" array[i]: "+array[i]);
        return false;
      }
    }
    
    return true;
  }
}
