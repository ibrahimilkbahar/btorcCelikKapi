package tr.com.mindworks.controllers.security;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Provider;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.services.UserService;

@Component("mainPageController")
@SpringFlowScoped
@ViewScoped
public class MainPageController extends BaseController {

	private CartesianChartModel combinedModel;
	
	@Autowired
	private Provider<UserService> userService;
	@Getter
	@Setter
	private String currentPassword;
	@Getter
	@Setter
	private String newPassword;
	@Getter
	@Setter
	private String newRePassword;
	
	
    @PostConstruct
    public void init() {
        createCombinedModel();
    }
 
    public CartesianChartModel getCombinedModel() {
        return combinedModel;
    }
     
    private void createCombinedModel() {
        combinedModel = new BarChartModel();
 
        BarChartSeries boys = new BarChartSeries();
        boys.setLabel("Satış Adet");
 
        boys.set("Ocak", 6);
        boys.set("Şubat", 5);
        boys.set("Mart", 1);
        boys.set("Nisan", 7);
        boys.set("Mayıs", 1);
 
        LineChartSeries girls = new LineChartSeries();
        girls.setLabel("Üretim Tamamlama");
 
        girls.set("Ocak", 2);
        girls.set("Şubat", 3);
        girls.set("Mart", 6);
        girls.set("Nisan", 7);
        girls.set("Mayıs", 6);
 
        combinedModel.addSeries(boys);
        combinedModel.addSeries(girls);
         
        combinedModel.setTitle("Satış / Üretim Grafiği");
        combinedModel.setLegendPosition("ne");
        combinedModel.setMouseoverHighlight(false);
        combinedModel.setShowDatatip(false);
        combinedModel.setShowPointLabels(true);
        Axis yAxis = combinedModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
    }
    
     
    public void passwordUpdate() {  	
    	if(!visit.getUser().getPassword().equals(currentPassword)){
    		jsfMessageUtil.addErrorMessage("Şifre güncellenemedi.Mevcut Şifre Hatalı");
    		return;
    	}
    	if(!newPassword.equals(newRePassword)){
    		jsfMessageUtil.addErrorMessage("Şifre güncellenemedi.Yeni Şifreler uyumlu değil.");
    		return;
    	}
    	if(newPassword==""){
    		jsfMessageUtil.addErrorMessage("Şifre güncellenemedi.Yeni Şifre Boş geçilemez.");
    		return;
    	}

		visit.getUser().setPassword(newPassword);
		userService.get().updateUserPassword(visit.getUser());
		jsfMessageUtil.addInfoMessage("Şifre Başarıyla güncellendi.");
    	
	}
    
   
}
