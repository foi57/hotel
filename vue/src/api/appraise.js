import request from '../request.js'

export default {
    updateAppraise: (form) => {
        return request({
            url: '/appraise/insert',
            method: 'post',
            data: form
        })
    },
}