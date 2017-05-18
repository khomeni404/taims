<#import "../taim_template/taims_layout.ftl" as layout>
<@layout.taims_layout "Test" >
    <#assign ctx = rc.getContextPath()/>

<div class="row">
    <div class="col-lg-12 text-center">
        <div class="alert alert-danger">
            <button class="close" data-dismiss="alert">
                <i class="ace-icon fa fa-times"></i>
            </button>
            <b>${message!}</b>
            <hr />
            <h4 class="lighter smaller" style="font-weight: bolder">
                Please ensure your CASM
                <i class="ace-icon fa fa-wrench icon-animated-wrench bigger-125"></i>
                settings !
            </h4>

        </div>
    </div>
</div>
</@layout.taims_layout>