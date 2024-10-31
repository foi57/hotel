import request from '../request.js'

export default {
    booking: (bookInfo) => {
        console.log(bookInfo);
        return request({
            url: '/booking',
            method: 'post',
            data: bookInfo
        })
    },
    showBook: () => {
        return request({
            url: '/showBook',
            method: 'get'
        })
    }
}