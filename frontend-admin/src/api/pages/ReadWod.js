import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const getWodById = async (id) => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/admin/api/wod/${id}`,
        withCredentials: true,
    }

    return await axios(config);
};

const deleteWodById = async (id) => {

    const config = {
        method: 'DELETE',
        url: `${backEndBaseUri}/admin/api/wod/${id}`,
        withCredentials: true,
    }

    return await axios(config);
};

export { getWodById, deleteWodById };