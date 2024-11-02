import request from '../request.js'

export default {
    addHotel: (form) => {
        return request({
            url: '/addHotel',
            method: 'post',
            data: form
        })
    },
    deletePicture: (fileName) => {
        console.log('fileId',fileName)
        return request({
            url: '/deletePicture',
            method: 'post',
            params: {
                fileName
            }
        })
    },
    selectHotel: () => {
        return request({
            url: '/selectSpecialsHotels',
            method: 'get'
        })
    },
    selectHotelByCityTime: (city,timeStart,timeEnd,page,pageSize) => {
        return request({
            url: '/selectHotelByCityTime',
            method: 'post',
            params: {
                city,
                timeStart,
                timeEnd,
                page,
                pageSize
            }
        })
    },
    selectRoomByHotelIdTime: (hotelId,timeStart,timeEnd) => {
        return request({
            url: '/selectRoomByHotelIdTime',
            method: 'post',
            params: {
                hotelId,
                timeStart,
                timeEnd
            }
        })
    }
}