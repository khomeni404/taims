<#import "../taim_template/taims_layout.ftl" as layout>
<@layout.taims_layout "${(title)!}" "${(username)!}" "${(userId)!}" "${(userName)!}">
    <#assign ctx = rc.getContextPath()/>
<div class="row">
    <div class="col-lg-12 text-center">
        <div class="alert alert-success">
            <button class="close" data-dismiss="alert">
                <i class="ace-icon fa fa-times"></i>
            </button>
            ${message!'Welcome to Task And Issue Management System (TAIMS)'}

        </div>
    </div>
</div>


</@layout.taims_layout>