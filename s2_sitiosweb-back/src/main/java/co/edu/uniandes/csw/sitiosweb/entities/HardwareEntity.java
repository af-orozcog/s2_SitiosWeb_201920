package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author s.santosb
 */
@Entity
public class HardwareEntity extends BaseEntity implements Serializable{
    
    private Long ip;
    private Integer cores;
    private Integer ram;
    private String cpu;
    private String plataforma;
    
    @PodamExclude
    @OneToOne
    private ProjectEntity project;


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

    /**
     * @return the project
     */
    public ProjectEntity getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(ProjectEntity project) {
        this.project = project;
    }
    
  @Override
  public boolean equals(Object o) {
    if (this == o) {
        return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    HardwareEntity other = (HardwareEntity)o;
    return this.ip == other.ip;
  }
  
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.cores);
        return hash;
    }

}
