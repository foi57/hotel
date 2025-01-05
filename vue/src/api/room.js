import request from "../request.js";

export default {
    update: (form) => {
        return request({
            url: `/updateRoom`,
            method: "POST",
            data: form
        })
    }
}