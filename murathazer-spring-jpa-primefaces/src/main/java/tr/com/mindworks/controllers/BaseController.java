package tr.com.mindworks.controllers;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tr.com.mindworks.model.Visit;
import tr.com.mindworks.util.JSFMessageUtil;

/**
 * @author mhazer on 15/08/16.
 */
public abstract class BaseController implements Serializable
{
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JSFMessageUtil jsfMessageUtil;

    @Autowired(required = false)
    protected Visit visit;

}
