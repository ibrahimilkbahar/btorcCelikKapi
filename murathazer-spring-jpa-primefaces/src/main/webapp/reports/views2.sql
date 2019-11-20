alter view vTeklif As
select 
    fGetValueWithCurrency(hh._InstallationAmount,hh.CurrencyValue,hh.CurrencyCode) InstallationAmount, 
    fGetValueWithCurrency(hh._TransportAmount,hh.CurrencyValue,hh.CurrencyCode) TransportAmount,
    fGetValueWithCurrency(hh._TotalCost,hh.CurrencyValue,hh.CurrencyCode) TotalCost,
    fGetValueWithCurrency(hh._TotalCost*-1*_FinalDiscount/100,hh.CurrencyValue,hh.CurrencyCode) FinalDiscount,
	
    
    fGetValueWithCurrency((hh._TotalCost-(hh._TotalCost*_FinalDiscount/100) +hh._InstallationAmount +hh._TransportAmount)*18/100,hh.CurrencyValue,hh.CurrencyCode) TaxValue,
    
    
    fGetValueWithCurrency(hh._InstallationAmount+
						  hh._TransportAmount+
                          hh._TotalCost+
                          (hh._TotalCost*-1*_FinalDiscount/100)+
                          (hh._TotalCost-(hh._TotalCost*_FinalDiscount/100) +hh._InstallationAmount +hh._TransportAmount )*18/100,hh.CurrencyValue,hh.CurrencyCode) FinalResult, 
 hh.*
from (SELECT o.ID,
    o.OrderNo,
    o.OrderDate,
    o.PaymentInformation,

    p.ProposalNo,

    p.CreateDate ProposalDate,
    p.LastSendDate,
    p.LastUpdateDate,
    p.RevisionDate,
    p.RevisionNo,
    
	o.CurrencyCode,
    o.CurrencyValue,
    
    o.InstallationAmount _InstallationAmount, 
    o.TransportAmount _TransportAmount, 
	o.FinalDiscount _FinalDiscount,
    (select sum(aa.TotalAmount) from t_order_offering aa where aa.orderId = o.ID) _TotalCost, 
 
  
    
    o.InstallationDescription, 
    o.InstallationRequested, 

    o.TransportRequested, 
    o.TransportDescription,
    
    c.Fullname CustomerFullName,
    c.IsCorporate,
    concat(concat(c.TaxOffice, '/No: '),c.taxId) TaxOfficeAndNo,
    c.Phone,
    c.Fax,
    c.Email,
    cc.Fullname CustomerContact,
    cc.Email CustomerContactEmail,
    cpo.name ContactPosition,
    
    concat(concat(ca.addressName, ' : '),ca.AddressText) Address1,
    concat(concat(concat(concat(d.name,' / '),ci.name),' / '),co.name) Address2,
    cp.ProjectName,
    
	concat(concat(ca1.addressName, ' : '),ca1.AddressText) DeliveryAddress1,
    concat(concat(concat(concat(d1.name,' / '),ci1.name),' / '),co1.name) DeliveryAddress2,
    o.CommitmentDate,

    u.fullname SalesResponsible,
    u2.fullname Approver,
    
    oo.Quantity,
    fGetValueWithCurrency(oo.InstanceAmount,o.CurrencyValue,o.CurrencyCode) InstanceAmount, 
    fGetValueWithCurrency(oo.TotalAmount,o.CurrencyValue,o.CurrencyCode) TotalAmount, 

    concat(concat(concat(concat(oo.ProductionWidth,' / '),oo.ProductionHeight),' / '),oo.EnteredDepth) Dimension,
    concat(concat(concat(concat(oo.EnteredWidth,' / '),oo.EnteredHeight),' / '),oo.EnteredDepth) EnteredDimension,
    
	replace(off.ImagePath,'/','\\') ImagePath,
	kk.*
FROM T_Order o
JOIN t_proposal p on o.Id = p.orderId
JOIN T_Order_Offering oo on o.ID = oo.OrderId
JOIN T_Offering off ON oo.offeringId = off.Id
JOIN T_user u on o.salesResponsibleId = u.ID
LEFT JOIN T_user u2 on o.ApproverId = u2.ID
JOIN (SELECT 
	aa.OrderOfferingID,
    aa.OfferingName,
    aa.ProductType,
    aa.ProductSurfaceType,
    aa.ProductWingType,
    aa.ProductCaseType,
    aa.OrderOfferingDescription,
    group_concat(aa.propertyValue separator ' -- ') PropertyValueText
FROM vOrderOfferingDetails aa
group by
	aa.OrderOfferingID,
    aa.OfferingName,
    aa.ProductType,
    aa.ProductSurfaceType,
    aa.ProductWingType,
    aa.ProductCaseType,
    aa.OrderOfferingDescription) kk on kk.orderOfferingID=oo.ID
JOIN t_customer c on o.customerId = c.ID
LEFT JOIN t_customer_contact cc on o.customerContactId = cc.ID
LEFT JOIN t_contact_position cpo on cc.contactPositionId = cpo.ID
LEFT JOIN t_customer_address ca on c.ID = ca.CustomerId and ca.IsCentralAddress = 1
LEFT JOIN T_district d on ca.districtId = d.Id
LEFT JOIN t_city ci on d.cityID = ci.ID
LEFT JOIN t_country co on ci.countryID = co.ID
LEFT JOIN t_customer_Project cp on o.CustomerProjectId = cp.ID

LEFT JOIN t_customer_address ca1 on o.DeliveryAddressId = ca1.Id
LEFT JOIN T_district d1 on ca1.districtId = d1.Id
LEFT JOIN t_city ci1 on d1.cityID = ci1.ID
LEFT JOIN t_country co1 on ci1.countryID = co1.ID
) hh;


alter view vOrderOfferingDetails as 
SELECT  oo.ID OrderOfferingID,
	off.name OfferingName,
	prt.name ProductType,
    prst.name ProductSurfaceType,
    prwt.name ProductWingType,
    prct.name ProductCaseType,
	oo.description OrderOfferingDescription,


	CASE
    WHEN pt.Code = 'TEXT' and pv.valueText is not null and pv.valueText!="" 	THEN concat(concat(p.Name, ': '),IFNULL(pv.valueText,""))
    WHEN pt.Code = 'NUMBER' and pv.valueNumber is not null and pv.valueNumber!=""    THEN concat(concat(p.Name, ': '),IFNULL(pv.valueNumber,""))
    WHEN pt.Code = 'LIST' and pl.id is not null     	THEN concat(concat(p.Name, ': '),IFNULL(pl.Name,""))
    END as PropertyValue
	

FROM T_Order_Offering oo

JOIN t_offering off on oo.OfferingID = off.Id
JOIN t_product_type prt on off.productTypeId = prt.ID
JOIN t_product_surface_type prst on off.productSurfaceTypeId = prst.ID
JOIN t_product_wing_type prwt on off.productWingTypeId = prwt.ID
JOIN t_product_case_type prct on oo.productCaseTypeId = prct.ID


LEFT JOIN T_Order_offering_Propval oopv ON oo.ID = oopv.OrderOfferingID and oopv.IsVisibleOnReport= 1
LEFT JOIN T_Property_value pv ON oopv.propertyValueId = pv.ID
LEFT JOIN T_Property p ON pv.propertyID = p.ID 
LEFT JOIN T_Property_Type pt on p.propertyTypeId = pt.ID
LEFT JOIN t_property_group pg on p.propertyGroupId = pg.ID
LEFT JOIN T_Property_Lov pl ON pv.propertyLovId = pl.ID
Order by pg.orderby,oopv.orderby;

alter view vProductInfo as
SELECT orr.Id orderId,	
    pi.Id productInstanceId,
    orr.orderNo,
    pi.ProductNo,
    pi.OrderBy,
    
    CONCAT(CONCAT(CONCAT(CONCAT(`oo`.`ProductionWidth`, ' / '), `oo`.`ProductionHeight`), ' / '), `oo`.`EnteredDepth`) AS `Dimension`,
    CONCAT(CONCAT(CONCAT(CONCAT(`oo`.`EnteredWidth`, ' / '), `oo`.`EnteredHeight`), ' / '), `oo`.`EnteredDepth`) AS `EnteredDimension`,
    kk.*
FROM
    t_product_instance pi 
    JOIN t_order_offering oo ON pi.orderOfferingId = oo.ID
    JOIN t_order orr ON oo.orderId = orr.ID
    JOIN (SELECT 
			aa.OrderOfferingID,
            aa.OfferingName,
            aa.ProductType,
            aa.ProductSurfaceType,
            aa.ProductWingType,
            aa.ProductCaseType,
            aa.OrderOfferingDescription,
            GROUP_CONCAT(aa.propertyValue  SEPARATOR ' \r\n ') PropertyValueText
    FROM vOrderOfferingDetails aa
    GROUP BY aa.OrderOfferingID , 
			 aa.OfferingName , 
             aa.ProductType , 
             aa.ProductSurfaceType , 
             aa.ProductWingType , 
             aa.ProductCaseType , 
             aa.OrderOfferingDescription) kk ON kk.orderOfferingID = oo.ID
             order by oo.Id,pi.orderby;
             
             
drop FUNCTION fGetValueWithCurrency;
DELIMITER //
create FUNCTION fGetValueWithCurrency(dValue double,currencyValue double,currencyCode CHAR(250))
    RETURNS CHAR(250)
    BEGIN
        DECLARE result CHAR(250);
        SET result=concat(concat(round(dValue/currencyValue,2),' '),currencyCode);
        RETURN result;
    END //

DELIMITER ;



 