package until;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtil {
	/**  
     * 对象转数组  
     * @param obj  
     * @return  
     */  
    public static byte[] toByteArray (ParamObject obj) {      
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;      
        ObjectOutputStream oos = null;
        try {        
        	bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray ();      
        } catch (IOException ex) {        
            ex.printStackTrace();   
        } finally {
        	if(oos != null) {
        		try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	if(bos != null) {
        		try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}         
        	}
        }
        return bytes;    
    }   
       
    /**  
     * 数组转对象  
     * @param bytes  
     * @return  
     */  
    public static ParamObject toObject (byte[] bytes) {      
        Object obj = null;      
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {        
            bis = new ByteArrayInputStream (bytes);        
            ois = new ObjectInputStream (bis);        
            obj = ois.readObject();      
        } catch (IOException ex) {        
            ex.printStackTrace();   
        } catch (ClassNotFoundException ex) {        
            ex.printStackTrace();   
        } finally {
        	if(ois != null) {
        		try {
        			ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	if(bis != null) {
        		try {
        			bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}         
        	}
        }
        return (ParamObject)obj;    
    }   
}
