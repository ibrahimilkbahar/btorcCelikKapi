package tr.com.mindworks.controllers.offering;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Provider;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TDimension;
import tr.com.mindworks.model.TFullCaseDepthPriceRange;
import tr.com.mindworks.model.TProductCaseType;
import tr.com.mindworks.model.TProductType;
import tr.com.mindworks.model.TProductWingType;
import tr.com.mindworks.services.DimensionService;
import tr.com.mindworks.services.ProductService;
 

@Component("dimensionController")
@SpringFlowScoped
@SessionScope
public class DimensionController extends BaseController {

	@Autowired
	private Provider<DimensionService> dimensionService;
	
	@Autowired
	private Provider<ProductService> productService;
	
	@Getter
	@Setter
	private TDimension newDimension;
	@Getter
	@Setter
	private List<TDimension> dimensionList;


	@Getter
	@Setter
	private List<TProductType> productTypeList;
	@Getter
	@Setter
	private List<TProductWingType> productWingTypeList;
	@Getter
	@Setter
	private List<TProductCaseType> productCaseTypeList;
	
	@Getter
	@Setter
	private Integer productTypeId,productWingTypeId,productCaseTypeId;

 
 
	@Getter
	@Setter
	private TFullCaseDepthPriceRange newFullCaseDepthPriceRange;
	@Getter
	@Setter
	private List<TFullCaseDepthPriceRange> fullCaseDepthPriceRangeList;
  
	
	public void onProductChange(final AjaxBehaviorEvent event) {
		dimensionList = dimensionService.get().findAllDimensionsBy_Type_Wing(productTypeId,productWingTypeId);
		
		if(TProductCaseType.CASE_TYPE_FULLCASE.getId().equals(productCaseTypeId)) {
			fullCaseDepthPriceRangeList = dimensionService.get().findAllFullCaseDepthPriceRanges(productTypeId,productWingTypeId);
		}
	}

	
	@PostConstruct
	public void initDimensionList() {
		productTypeList = productService.get().findAllProductTypes();
		productWingTypeList = productService.get().findAllProductWingTypes();
		productCaseTypeList = productService.get().findAllProductCaseTypes();
		newDimension = new TDimension();
		newFullCaseDepthPriceRange = new TFullCaseDepthPriceRange();
		
		
		productTypeId = productTypeList.get(0).getId();
		productWingTypeId = productWingTypeList.get(0).getId();
		productCaseTypeId = productCaseTypeList.get(0).getId();
		
		onProductChange(null);
	}
 
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            DataTable dataTable = (DataTable) event.getSource();
            TDimension dimension = (TDimension) dataTable.getRowData();
            dimensionService.get().saveDimension(dimension);
        }
    }
	
	public void onCellEditCasePrice(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            DataTable dataTable = (DataTable) event.getSource();
            TFullCaseDepthPriceRange tFullCaseDepthPriceRange = (TFullCaseDepthPriceRange) dataTable.getRowData();
    		dimensionService.get().saveFullCaseDepthPriceRange(tFullCaseDepthPriceRange);
        }
    }

	 

	public void deleteDimension(TDimension dimension) {
		dimensionService.get().deleteDimension(dimension.getId());
		dimensionList = dimensionService.get().findAllDimensionsBy_Type_Wing(productTypeId,productWingTypeId);
	}
	
	public void saveDimension(ActionEvent actionEvent) {
		newDimension.setProductType(new TProductType(productTypeId));
		newDimension.setProductWingType(new TProductWingType(productWingTypeId));
		
		dimensionService.get().saveDimension(newDimension);
		dimensionList = dimensionService.get().findAllDimensionsBy_Type_Wing(productTypeId,productWingTypeId);
	}

	public void resetDimension() {
		newDimension = new TDimension();
	}

	public void loadDimension(TDimension dimension) {
		newDimension = dimension;
	}
	
 
	public void saveFullCaseDepthPriceRange(ActionEvent actionEvent) {
		dimensionService.get().saveFullCaseDepthPriceRange(newFullCaseDepthPriceRange);
		fullCaseDepthPriceRangeList = dimensionService.get().findAllFullCaseDepthPriceRanges(productTypeId,productWingTypeId);
	}

	public void resetFullCaseDepthPriceRange() {
		newFullCaseDepthPriceRange = new TFullCaseDepthPriceRange();
	}

	public void loadFullCaseDepthPriceRange(TFullCaseDepthPriceRange fullCaseDepthPriceRange) {
		newFullCaseDepthPriceRange = fullCaseDepthPriceRange;
	}
	
	
}
