import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const updateWod = async (id, date, title, content) => {

    const data = {
        id: id,
        date: date,
        title: title,
        content: content,
    }

    const config = {
        method: 'PUT',
        url: `${backEndBaseUri}/admin/api/wod`,
        data: data,
        withCredentials: true,
    }

    return await axios(config);
};

export { updateWod };