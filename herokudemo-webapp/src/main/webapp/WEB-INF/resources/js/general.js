/**
 * Created by Vu Anh Nguyen Duc on 6/20/2017.
 */
/*
    PREVIEW AN IMAGE BEFORE UPLOAD
 */

function readURL(input){
    if(input.files && input.files[0]){
        var reader = new FileReader();

        reader.onload = function (e) {
               $("#imagePreview")
                   .attr("src", e.target.result)
                   .width(400)
                   .height(400)
        };

        reader.readAsDataURL(input.files[0]);
    }
}