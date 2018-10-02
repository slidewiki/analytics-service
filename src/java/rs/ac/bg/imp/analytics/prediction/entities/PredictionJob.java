package rs.ac.bg.imp.analytics.prediction.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author korisnik
 */
@Entity
@Table(name = "prediction_job")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PredictionJob.findAll", query = "SELECT p FROM PredictionJob p")
    , @NamedQuery(name = "PredictionJob.findById", query = "SELECT p FROM PredictionJob p WHERE p.id = :id")
    , @NamedQuery(name = "PredictionJob.findByStarted", query = "SELECT p FROM PredictionJob p WHERE p.started = :started")
    , @NamedQuery(name = "PredictionJob.findByFinished", query = "SELECT p FROM PredictionJob p WHERE p.finished = :finished")
    , @NamedQuery(name = "PredictionJob.findByDeckId", query = "SELECT p FROM PredictionJob p WHERE p.deckId = :deckId")
    , @NamedQuery(name = "PredictionJob.findByUserId", query = "SELECT p FROM PredictionJob p WHERE p.userId = :userId")
    , @NamedQuery(name = "PredictionJob.findByResult", query = "SELECT p FROM PredictionJob p WHERE p.result = :result")
    , @NamedQuery(name = "PredictionJob.findByAccuracy", query = "SELECT p FROM PredictionJob p WHERE p.accuracy = :accuracy")})
public class PredictionJob implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "started")
    @Temporal(TemporalType.TIMESTAMP)
    private Date started;
    @Column(name = "finished")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finished;
    @Size(max = 45)
    @Column(name = "deck_id")
    private String deckId;
    @Size(max = 45)
    @Column(name = "user_id")
    private String userId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "result")
    private Double result;
    @Column(name = "accuracy")
    private Double accuracy;
    @Column(name = "no_of_users")
    private Integer noOfUsers;
    @Column(name = "no_of_decks")
    private Integer noOfDecks;
    
    private String relatedPredictionActivityId;

    public PredictionJob() {
    }

    public PredictionJob(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getNoOfUsers() {
        return noOfUsers;
    }

    public void setNoOfUsers(Integer noOfUsers) {
        this.noOfUsers = noOfUsers;
    }

    public Integer getNoOfDecks() {
        return noOfDecks;
    }

    public void setNoOfDecks(Integer noOfDecks) {
        this.noOfDecks = noOfDecks;
    }

    public String getRelatedPredictionActivityId() {
        return relatedPredictionActivityId;
    }

    public void setRelatedPredictionActivityId(String relatedPredictionActivityId) {
        this.relatedPredictionActivityId = relatedPredictionActivityId;
    }
        

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PredictionJob)) {
            return false;
        }
        PredictionJob other = (PredictionJob) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.imp.analytics.prediction.entities.PredictionJob[ id=" + id + " ]";
    }
    
}
