/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */
@Entity
@Table(name = "disease_categories")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "DISEASE_CATEGORIES_ID_SEQ") })
public class DiseaseCategory extends AbstractMutableDeletableDomainObject {
    private String name;

    private List<DiseaseTerm> terms = new ArrayList<DiseaseTerm>();

    private DiseaseCategory parentCategory;

    private List<DiseaseCategory> childCategories = new ArrayList<DiseaseCategory>();

    // / LOGIC
    
    //commented addTerm method since it is not being used by anybody

//    public void addTerm(DiseaseTerm term) {
//        terms.add(term);
//        term.setCategory(this);
//    }

    // //// BEAN PROPERTIES

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category")
    @OrderBy
    // by ID for consistency
    public List<DiseaseTerm> getTerms() {
        return terms;
    }

    public void setTerms(List<DiseaseTerm> terms) {
        this.terms = terms;
    }

    @OneToMany(mappedBy = "parentCategory", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public List<DiseaseCategory> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<DiseaseCategory> childCategories) {
        this.childCategories = childCategories;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    public DiseaseCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(DiseaseCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

}
