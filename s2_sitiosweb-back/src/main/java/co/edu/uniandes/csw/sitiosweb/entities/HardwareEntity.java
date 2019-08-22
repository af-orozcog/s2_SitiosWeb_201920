/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author s.santosb
 */
@Entity
public class HardwareEntity extends BaseEntity implements Serializable{
    
    private Long ip;
    private int cores;
    private int ram;
    private String cpu;
    private String plataforma;

    /**
     * @return the ip
     */
    public Long getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(Long ip) {
        this.ip = ip;
    }

    /**
     * @return the cores
     */
    public int getCores() {
        return cores;
    }

    /**
     * @param cores the cores to set
     */
    public void setCores(int cores) {
        this.cores = cores;
    }

    /**
     * @return the ram
     */
    public int getRam() {
        return ram;
    }

    /**
     * @param ram the ram to set
     */
    public void setRam(int ram) {
        this.ram = ram;
    }

    /**
     * @return the cpu
     */
    public String getCpu() {
        return cpu;
    }

    /**
     * @param cpu the cpu to set
     */
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    /**
     * @return the plataforma
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * @param plataforma the plataforma to set
     */
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
    
    
}
