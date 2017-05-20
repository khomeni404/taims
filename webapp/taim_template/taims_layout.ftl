<#macro taims_layout title username userId userName>
    <#assign  ctx = rc.getContextPath()>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>${title!}</title>
    <#include "resources.ftl"/>
</head>

<body class="no-skin">
<div id="navbar" class="navbar navbar-default">
    <script type="text/javascript">
        try{ace.settings.check('navbar' , 'fixed')}catch(e){}
    </script>

    <#include "nav_bar.ftl"/>
</div>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <div id="sidebar" class="sidebar                  responsive">
        <script type="text/javascript">
            try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
        </script>

        <#include "side_bar.ftl"/>
        <#include "menu.ftl"/>

        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>

        <script type="text/javascript">
            try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
        </script>
    </div>

    <div class="main-content">
        <div class="main-content-inner">
            <#include "breadcrumbs.ftl"/>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <#nested>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="footer">
        <div class="footer-inner">
            <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">TAIMS</span>
							IBBL &copy; 2013-2014
						</span>
                &nbsp; &nbsp;
            </div>
        </div>
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div>

    <#include "resources_footer.ftl"/>
</body>
</html>


</#macro>