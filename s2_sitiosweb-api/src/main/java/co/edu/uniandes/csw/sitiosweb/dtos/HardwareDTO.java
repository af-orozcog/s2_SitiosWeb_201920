/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author s.santosb
 */
public class HardwareDTO implements Serializable{
    
    private Long id;
    private Long ip;
    private int cores;
    private int ram;
    private String cpu;
    private String plataforma;
   

    /**
     * Constructor vacio
     */
    public HardwareDTO() {
    }

    /**
     * Crea un objeto HardwareDTO a partir de un objeto HardwareEntity.
     *
     * @param hardwareEntity Entidad HardwareEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public HardwareDTO(HardwareEntity hardwareEntity) {
        if (hardwareEntity != null) {
            this.id = hardwareEntity.getId();
            this.ip = hardwareEntity.getIp();
            this.cores = hardwareEntity.getCores();
            this.ram = hardwareEntity.getRam();
            this.cpu = hardwareEntity.getCpu();
            this.plataforma = hardwareEntity.getPlataforma();
    
        }
    }

    /**
     * Convierte un objeto HardwareDTO a HardwareEntity.
     *
     * @return Nueva objeto HardwareEntity.
     *
     */
    public HardwareEntity toEntity() {
        HardwareEntity hardwareEntity = new HardwareEntity();
        hardwareEntity.setId(this.getId());
        hardwareEntity.setIp(this.getIp());
        hardwareEntity.setCores(this.getCores());
        hardwareEntity.setRam(this.getRam());
        hardwareEntity.setCpu(this.getCpu());
        hardwareEntity.setPlataforma(this.getPlataforma());
        return hardwareEntity;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

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
